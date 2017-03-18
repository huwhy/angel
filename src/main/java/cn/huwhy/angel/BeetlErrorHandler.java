package cn.huwhy.angel;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.beetl.core.ErrorHandler;
import org.beetl.core.Resource;
import org.beetl.core.ResourceLoader;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.ErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huwhy
 * @date 2016/12/27
 * @Desc
 */
public class BeetlErrorHandler implements ErrorHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void processExcption(BeetlException ex, Writer writer) {

        ErrorInfo error = new ErrorInfo(ex);

        if (error.getErrorCode().equals(BeetlException.CLIENT_IO_ERROR_ERROR)) {
            //不输出详细提示信息
                println(writer, "客户端IO异常:" + getResourceName(ex.resourceId) + ":" + error.getMsg());
                if (ex.getCause() != null) {
                    this.printThrowable(writer, ex.getCause());
                }
                return;

        }

        int line = error.getErrorTokenLine();

        StringBuilder sb = new StringBuilder(">>").append(this.getDateTime()).append(":").append(error.getType())
                .append(":").append(error.getErrorTokenText()).append(" 位于").append(line).append("行").append(" 资源:")
                .append(getResourceName(ex.resourceId));

        if (error.getErrorCode().equals(BeetlException.TEMPLATE_LOAD_ERROR)) {
            if (error.getMsg() != null)
                sb.append(error.getMsg());
            println(writer, sb.toString());
            println(writer, ex.gt.getResourceLoader().getInfo());
            return;
        }

        println(writer, sb.toString());
        if (ex.getMessage() != null) {
            println(writer, ex.getMessage());
        }

        ResourceLoader resLoader = ex.gt.getResourceLoader();
        //潜在问题，此时可能得到是一个新的模板（开发模式下），不过可能性很小，忽略！

        String content = null;
        try {
            Resource res = resLoader.getResource(ex.resourceId);
            //显示前后三行的内容
            int[] range = this.getRange(line);
            content = res.getContent(range[0], range[1]);
            if (content != null) {
                String[] strs = content.split(ex.cr);
                int lineNumber = range[0];
                for (int i = 0; i < strs.length; i++) {
                    print(writer, "" + lineNumber);
                    print(writer, "|");
                    println(writer, strs[i]);
                    lineNumber++;
                }

            }
        } catch (IOException e) {

            //ingore

        }

        if (error.hasCallStack()) {
            println(writer, "  ========================");
            println(writer, "  调用栈:");
            for (int i = 0; i < error.getResourceCallStack().size(); i++) {
                println(writer, "  " + error.getResourceCallStack().get(i) + " 行："
                        + error.getTokenCallStack().get(i).line);
            }
        }

        printCause(error, writer);
        try {
            writer.flush();
        } catch (IOException e) {

        }

    }

    protected void printCause(ErrorInfo error, Writer writer) {
        Throwable t = error.getCause();
        if (t != null) {
            printThrowable(writer, t);
        }

    }

    protected String getResourceName(String resourceId) {
        return resourceId;
    }

    protected void println(Writer w, String msg) {
        logger.error(msg);
    }

    protected void print(Writer w, String msg) {
        logger.error(msg);
    }

    protected void printThrowable(Writer w, Throwable t) {
        logger.error("beetl error: ", t);
    }

    protected int[] getRange(int line) {
        int startLine = 0;
        int endLine = 0;
        if (line > 3) {
            startLine = line - 3;
        } else {
            startLine = 1;
        }

        endLine = startLine + 6;
        return new int[]
                {startLine, endLine};
    }

    protected String getDateTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        return sdf.format(date);
    }
}
