package co.biogram.media;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class PassThroughTrackTranscoder implements TrackTranscoder
{
    private final MediaExtractor mExtractor;
    private final int mTrackIndex;
    private final QueuedMuxer mMuxer;
    private final QueuedMuxer.SampleType mSampleType;
    private final MediaCodec.BufferInfo mBufferInfo = new MediaCodec.BufferInfo();
    private final ByteBuffer mBuffer;
    private boolean mIsEOS;
    private long mWrittenPresentationTimeUs;

    PassThroughTrackTranscoder(MediaExtractor extractor, int trackIndex, QueuedMuxer muxer, QueuedMuxer.SampleType sampleType)
    {
        mExtractor = extractor;
        mTrackIndex = trackIndex;
        mMuxer = muxer;
        mSampleType = sampleType;

        MediaFormat mActualOutputFormat = mExtractor.getTrackFormat(mTrackIndex);
        mMuxer.setOutputFormat(mSampleType, mActualOutputFormat);
        int mBufferSize = mActualOutputFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
        mBuffer = ByteBuffer.allocateDirect(mBufferSize).order(ByteOrder.nativeOrder());
    }

    @Override
    public void setup() { }


    @Override
    public boolean stepPipeline()
    {
        if (mIsEOS) return false;
        int trackIndex = mExtractor.getSampleTrackIndex();

        if (trackIndex < 0)
        {
            mBuffer.clear();
            mBufferInfo.set(0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
            mMuxer.writeSampleData(mSampleType, mBuffer, mBufferInfo);
            mIsEOS = true;
            return true;
        }

        if (trackIndex != mTrackIndex)
            return false;

        mBuffer.clear();
        int sampleSize = mExtractor.readSampleData(mBuffer, 0);
        boolean isKeyFrame = (mExtractor.getSampleFlags() & MediaExtractor.SAMPLE_FLAG_SYNC) != 0;
        int flags = isKeyFrame ? MediaCodec.BUFFER_FLAG_SYNC_FRAME : 0;
        mBufferInfo.set(0, sampleSize, mExtractor.getSampleTime(), flags);
        mMuxer.writeSampleData(mSampleType, mBuffer, mBufferInfo);
        mWrittenPresentationTimeUs = mBufferInfo.presentationTimeUs;
        mExtractor.advance();
        return true;
    }

    @Override
    public long getWrittenPresentationTimeUs()
    {
        return mWrittenPresentationTimeUs;
    }

    @Override
    public boolean isFinished()
    {
        return mIsEOS;
    }

    @Override
    public void release() { }
}