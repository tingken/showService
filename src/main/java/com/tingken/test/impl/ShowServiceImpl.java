/**
 * 
 */
package com.tingken.test.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.PreencodedMimeBodyPart;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import com.tingken.test.api.AuthResult;
import com.tingken.test.api.ServerCommand;
import com.tingken.test.api.ShowService;
import com.tingken.test.api.VersionInfo;
import com.tingken.test.exception.ShowServiceException;

/**
 * @author Administrator
 *
 */
public class ShowServiceImpl implements ShowService {
    private static final String ROOT_FIELDS = "root-fields";
    private static final String MULTIPART_TYPE = "multipart";
    private static final String MIXED_SUB_TYPE = "mixed";
    private static final String CONTENT_TYPE = "content-type";
    private static final String CXF_CONTENT_TYPE = "Content-Type";
    private static final String CXF_CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    
    private static int accessTime = 0;

	/* (non-Javadoc)
	 * @see com.tingken.test.api.ShowService#uploadCapture(java.lang.String, org.apache.cxf.jaxrs.ext.multipart.MultipartBody)
	 */
	public Response uploadCapture(String deviceId, MultipartBody body)
			throws ShowServiceException {
		Attachment root = body.getAttachment(ROOT_FIELDS);
		body.getAttachment("attachments");
		List<Attachment> attachments = body.getAllAttachments();
		if(attachments.size() != 1){
			throw new ShowServiceException("no attachment or count error");
		}
        try {
			MimeBodyPart mbp = createBodyPart(attachments.get(0), new MimeBodyPart());
			ByteArrayOutputStream helperByteStream = new ByteArrayOutputStream();
			mbp.writeTo(helperByteStream);
			byte[] content = helperByteStream.toByteArray();
		} catch (MessagingException e) {
			throw new ShowServiceException(e);
		} catch (IOException e) {
			throw new ShowServiceException(e);
		}
		return Response.ok().build();
	}

	public Response authenticate(String authCode, String deviceId, String dimension)
			throws ShowServiceException {
		AuthResult result = new AuthResult();
		result.setLoginId(authCode);
		result.setShowPageAddress("www.sohu.com");
		return Response.ok(result).build();
	}

	public ServerCommand heartBeat(String loginId) {
		ServerCommand command = new ServerCommand();
		command.setCommand(ServerCommand.CommandHead.NONE);
		if (++accessTime % 5 == 0) {
			if ("11111".equals(loginId)) {
				command.setCommand(ServerCommand.CommandHead.UPGRADE);
			} else if ("22222".equals(loginId)) {
				command.setCommand(ServerCommand.CommandHead.SCREEN_CAPTURE);
			} else if ("33333".equals(loginId)) {
				throw new RuntimeException("exception");
			} else if ("4444".equals(loginId)) {
				command.setCommand(ServerCommand.CommandHead.RESTART);
			}
		}
		return command;
	}

	public boolean uploadScreen(String authCode, Date captureTime, File capture) {
		// TODO Auto-generated method stub
		return false;
	}

    private static MultivaluedMap<String, Object> parseRequest(Attachment root) throws ShowServiceException {
        if (equalsContentTypeIgnoreParam(MediaType.APPLICATION_FORM_URLENCODED_TYPE, root.getContentType())) {
            MultivaluedMap<String, Object> rootMap =  root.getObject(MultivaluedMap.class);
            try {
                root.getDataHandler().getInputStream().reset();
            } catch (IOException e) {
                throw new ShowServiceException(e);
            }
            return rootMap;
        } else if (equalsContentTypeIgnoreParam(MediaType.APPLICATION_JSON_TYPE, root.getContentType())) {
//            return convert2Map(root.getObject(OutboundMessageRequest.class));
        }

        throw new ShowServiceException();
    }

    public static byte[] toMM7Attachment(Attachment attachment) throws ShowServiceException {
        try {
            MediaType mt = attachment.getContentType();
            if (MULTIPART_TYPE.equalsIgnoreCase(mt.getType()) && MIXED_SUB_TYPE.equalsIgnoreCase(mt.getSubtype())) {
                return encodeMixed(attachment);
            } else if (MULTIPART_TYPE.equalsIgnoreCase(mt.getType())
                    && !(MIXED_SUB_TYPE.equalsIgnoreCase(mt.getSubtype()))) {
                throw new ShowServiceException();
            }

            return encodeSingle(attachment);
        } catch (Exception e) {
            throw new ShowServiceException(e);
        }
    }

    private static boolean equalsContentTypeIgnoreParam(MediaType type1, MediaType type2) {
        return (type1.getType().equals(type2.getType()) && type1.getSubtype().equals(type2.getSubtype()));
    }

	private static byte[] encodeMixed(Attachment a) throws Exception {
		MimeBodyPart mbp = new MimeBodyPart();
		mbp.setDataHandler(a.getDataHandler());
		MultivaluedMap<String, String> h = a.getHeaders();
		for (Entry<String, List<String>> e : h.entrySet()) {
			List<String> v = e.getValue();
			for (String hv : v) {
				mbp.addHeader(e.getKey(), hv);
			}
		}
		mbp.removeHeader(CONTENT_TYPE);
		mbp.removeHeader(CXF_CONTENT_TYPE);
		mbp.addHeader(CONTENT_TYPE, a.getDataHandler().getContentType());
		ByteArrayOutputStream helperByteStream = new ByteArrayOutputStream();
		mbp.writeTo(helperByteStream);
		return helperByteStream.toByteArray();
	}

    private static byte[] encodeSingle(Attachment a) throws Exception {
        MimeBodyPart mbp = createBodyPart(a, new MimeBodyPart());

        String encoding = "|" + StringUtils.join(mbp.getHeader(CXF_CONTENT_TRANSFER_ENCODING), "|") + "|";
        if (StringUtils.containsIgnoreCase(encoding, "|base64|")) {
            mbp = createBodyPart(a, new PreencodedMimeBodyPart(a.getHeader(CXF_CONTENT_TRANSFER_ENCODING)));
        }

        ByteArrayOutputStream helperByteStream = new ByteArrayOutputStream();
        mbp.writeTo(helperByteStream);

        return helperByteStream.toByteArray();
    }

    private static MimeBodyPart createBodyPart(Attachment a, MimeBodyPart mbp) throws MessagingException {
        mbp.setDataHandler(a.getDataHandler());
        MultivaluedMap<String, String> h = a.getHeaders();
        for (Entry<String, List<String>> e : h.entrySet()) {
            List<String> v = e.getValue();
            for (String hv : v) {
                mbp.addHeader(e.getKey(), hv);
            }
        }

        return mbp;
    }

	public VersionInfo getLatestVersion() {
		VersionInfo version = new VersionInfo();
		version.setVersionCode(2);
		version.setVersionName("1.0.2");
		version.setDownloadAddress("http://192.168.2.8:8080/manager/InfoShower.apk");
		return version;
	}

}
