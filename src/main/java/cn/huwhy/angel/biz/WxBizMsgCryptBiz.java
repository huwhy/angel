package cn.huwhy.angel.biz;

import java.io.IOException;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cn.huwhy.angel.po.MpConfig;
import cn.huwhy.wx.sdk.aes.AesException;
import cn.huwhy.wx.sdk.aes.WXBizMsgCrypt;
import cn.huwhy.wx.sdk.model.Command;

@Component
public class WxBizMsgCryptBiz {
    private static Logger logger = LoggerFactory.getLogger(WxBizMsgCryptBiz.class);
    private static Map<String, WXBizMsgCrypt> msgCryptMap = new HashMap<>();
    private static Object                     lock        = new Object();

    public boolean check(MpConfig config, String signature, String timestamp, String nonce) throws AesException, NoSuchAlgorithmException {
        WXBizMsgCrypt crypt = getWxBizMsgCrypt(config);
        return crypt.check(signature, timestamp, nonce);
    }

    public Command transform(MpConfig config, String signature, String timestamp, String nonce, String postXML) {
        try {
            WXBizMsgCrypt crypt = getWxBizMsgCrypt(config);
            String ss = crypt.decryptMsg(signature, timestamp, nonce, postXML);
            logger.info("decrypt msg : " + ss);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(ss);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);
            Element root = document.getDocumentElement();

            Command command = new Command();
            NodeList msgTypeNode = root.getElementsByTagName("MsgType");
            String msgType = msgTypeNode.item(0).getTextContent().trim();
            command.setMsgType(msgType);

            NodeList toNode = root.getElementsByTagName("ToUserName");
            String toUser = toNode.item(0).getTextContent();
            command.setToUserName(toUser);

            NodeList fromNode = root.getElementsByTagName("FromUserName");
            String fromUser = fromNode.item(0).getTextContent();
            command.setFromUserName(fromUser);

            NodeList createTimeNode = root.getElementsByTagName("CreateTime");
            long createTime = Long.parseLong(createTimeNode.item(0).getTextContent());
            command.setCreateTime(createTime);

            NodeList eventNode = root.getElementsByTagName("Event");
            if (eventNode.getLength() == 1) {
                command.setEvent(eventNode.item(0).getTextContent());
            }

            NodeList ticketNode = root.getElementsByTagName("Ticket");
            if (ticketNode.getLength() == 1) {
                command.setTicket(ticketNode.item(0).getTextContent());
            }

            NodeList ekNode = root.getElementsByTagName("EventKey");
            if (ekNode.getLength() == 1) {
                command.setEventKey(ekNode.item(0).getTextContent());
            }

            NodeList latitudeNode = root.getElementsByTagName("Latitude");
            if (latitudeNode.getLength() == 1) {
                command.setLatitude(latitudeNode.item(0).getTextContent());
            }

            NodeList longitudeNode = root.getElementsByTagName("Longitude");
            if (longitudeNode.getLength() == 1) {
                command.setLongitude(longitudeNode.item(0).getTextContent());
            }

            NodeList precisionNode = root.getElementsByTagName("Precision");
            if (precisionNode.getLength() == 1) {
                command.setPrecision(precisionNode.item(0).getTextContent());
            }

            NodeList msgIdNode = root.getElementsByTagName("MsgId");
            if (msgIdNode.getLength() == 1) {
                command.setMsgId(msgIdNode.item(0).getTextContent());
            }

            NodeList contentNode = root.getElementsByTagName("Content");
            if (contentNode.getLength() == 1) {
                command.setContent(contentNode.item(0).getTextContent());
            }

            NodeList picUrlNode = root.getElementsByTagName("PicUrl");
            if (picUrlNode.getLength() == 1) {
                command.setPicUrl(picUrlNode.item(0).getTextContent());
            }

            NodeList mediaIdNode = root.getElementsByTagName("MediaId");
            if (mediaIdNode.getLength() == 1) {
                command.setMediaId(mediaIdNode.item(0).getTextContent());
            }

            NodeList formatNode = root.getElementsByTagName("Format");
            if (formatNode.getLength() == 1) {
                command.setFormat(formatNode.item(0).getTextContent());
            }

            command.setThumbMediaid(nodeValue("ThumbMediaId", root));
            command.setLocationX(nodeValue("Location_X", root));
            command.setLocationY(nodeValue("Location_Y", root));
            command.setScale(nodeValue("Scale", root));
            command.setLabel(nodeValue("Label", root));
            command.setTitle(nodeValue("Title", root));
            command.setDescription(nodeValue("Description", root));
            command.setUrl(nodeValue("Url", root));
            command.setRecognition(nodeValue("Recognition", root));
            command.setStatus(nodeValue("Status", root));
            return command;

        } catch (IOException | ParserConfigurationException | AesException | SAXException | NoSuchAlgorithmException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }

    private WXBizMsgCrypt getWxBizMsgCrypt(MpConfig config) throws AesException {
        WXBizMsgCrypt crypt = msgCryptMap.get(config.getAppId());
        if (crypt == null) {
            synchronized (lock) {
                crypt = msgCryptMap.get(config.getAppId());
                if (crypt == null) {
                    crypt = new WXBizMsgCrypt(config.getToken(), config.getAesKey(), config.getAppId());
                    msgCryptMap.put(config.getAppId(), crypt);
                }
            }
        }
        return crypt;
    }

    private String nodeValue(String nodeName, Element root) {
        NodeList node = root.getElementsByTagName(nodeName);
        if (node.getLength() == 1) {
            return node.item(0).getTextContent();
        }
        return null;
    }
}