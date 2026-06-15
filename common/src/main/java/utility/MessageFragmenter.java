package utility;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class MessageFragmenter {

    public static final int MTU = 1400;

    public static final int HEADER_SIZE = 12;

    public static final int MAX_PAYLOAD_SIZE = MTU - HEADER_SIZE;

    public static final int MAX_MESSAGE_SIZE = 1024 * 1024;

    private MessageFragmenter() {
    }

    public static class FragmentHeader {
        public final int messageId;
        public final int fragmentIndex;
        public final int totalFragments;

        public FragmentHeader(int messageId, int fragmentIndex, int totalFragments) {
            this.messageId = messageId;
            this.fragmentIndex = fragmentIndex;
            this.totalFragments = totalFragments;
        }

        @Override
        public String toString() {
            return String.format("Message[%d] Fragment[%d/%d]",
                    messageId, fragmentIndex, totalFragments);
        }
    }

    public static List<ByteBuffer> fragment(byte[] message, int messageId) {
        if (message.length > MAX_MESSAGE_SIZE) {
            throw new IllegalArgumentException(
                    "Message too large: " + message.length + " bytes (max: " + MAX_MESSAGE_SIZE + ")");
        }

        int totalFragments = (int) Math.ceil((double) message.length / MAX_PAYLOAD_SIZE);
        List<ByteBuffer> fragments = new ArrayList<>(totalFragments);

        for (int i = 0; i < totalFragments; i++) {
            int offset = i * MAX_PAYLOAD_SIZE;
            int length = Math.min(MAX_PAYLOAD_SIZE, message.length - offset);


            ByteBuffer buffer = ByteBuffer.allocate(HEADER_SIZE + length);

            buffer.putInt(messageId);
            buffer.putInt(i);
            buffer.putInt(totalFragments);


            buffer.put(message, offset, length);


            buffer.flip();

            fragments.add(buffer);
        }

        return fragments;
    }


    public static FragmentHeader extractHeader(ByteBuffer buffer) {
        if (buffer.remaining() < HEADER_SIZE) {
            return null;
        }

        int originalPosition = buffer.position();

        int messageId = buffer.getInt();
        int fragmentIndex = buffer.getInt();
        int totalFragments = buffer.getInt();

        buffer.position(originalPosition + HEADER_SIZE);

        return new FragmentHeader(messageId, fragmentIndex, totalFragments);
    }

    public static byte[] extractData(ByteBuffer buffer){
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        return data;
    }


    public static int calculateFragmentCount(int messageSize) {
        return (int) Math.ceil((double) messageSize / MAX_PAYLOAD_SIZE);
    }
}
