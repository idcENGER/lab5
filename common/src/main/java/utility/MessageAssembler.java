package utility;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageAssembler {

    private final int messageId;
    private final int totalFragments;
    private final Map<Integer, byte[]> fragments = new ConcurrentHashMap<>();
    private final long createTime = System.currentTimeMillis();
    private int currentTotalSize = 0;

    public MessageAssembler(int messageId, int totalFragments) {
        this.messageId = messageId;
        this.totalFragments = totalFragments;
    }


    public boolean addFragment(int fragmentIndex, byte[] data) {
        if (currentTotalSize + data.length > MessageFragmenter.MAX_MESSAGE_SIZE) {
            throw new IllegalStateException(
                    "Message " + messageId + " exceeds max size: " +
                            (currentTotalSize + data.length) + " bytes");
        }

        fragments.put(fragmentIndex, data);
        currentTotalSize += data.length;

        return fragments.size() == totalFragments;
    }


    public byte[] assemble() {
        ByteBuffer assembled = ByteBuffer.allocate(currentTotalSize);

        for (int i = 0; i < totalFragments; i++) {
            byte[] fragment = fragments.get(i);
            if (fragment == null) {
                throw new IllegalStateException(
                        "Missing fragment " + i + " for message " + messageId);
            }
            assembled.put(fragment);
        }

        return assembled.array();
    }

    public int getMessageId() {
        return messageId;
    }

    public int getTotalFragments() {
        return totalFragments;
    }

    public long getCreateTime() {
        return createTime;
    }

    public int getReceivedFragmentsCount() {
        return fragments.size();
    }
}