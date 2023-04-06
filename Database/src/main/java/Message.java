import com.mysql.cj.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

public abstract class Message {

    abstract String getMessageTitle();

    abstract String getMessageBody();

    protected void validateMessageTitleAndBody() throws SenderException{
        if(StringUtils.isEmptyOrWhitespaceOnly(getMessageTitle()))
            throw new SenderException("Message title is empty");
        if(StringUtils.isEmptyOrWhitespaceOnly(getMessageBody()))
            throw new SenderException("Message body is empty");
    }
    abstract void validateMessage() throws SenderException;

    String anonymizeMessageBody() {
        return DigestUtils.md5Hex(getMessageBody());
    }
}
