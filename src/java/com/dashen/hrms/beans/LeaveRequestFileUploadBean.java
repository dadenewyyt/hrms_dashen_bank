/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms.beans;

import com.dashen.hrms.Configuration;
import com.dashen.hrms.SpringViewScope;
import com.dashen.hrms.Util;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Math.random;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import org.apache.commons.io.FilenameUtils;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;
import oracle.sql.BLOB;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Component;


@Component
@SpringViewScope
public class LeaveRequestFileUploadBean {

    private SecureRandom random = new SecureRandom();
    private byte[] fileContents; //for preview

    private UploadedFile uploadedFile; // +getter+setter

    ServletContext servletContext;

    private String previewImage = "";
    
    private String divStyle = "display:none;";

    private final String firstPartData = "data:image/";
    private String secondPartExtension = "png";
    private final String thirdPartComma = ";base64,";
    private String finalImageContentBase64Encoded;

    @PostConstruct
    public void init() {
        servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        System.out.println(servletContext.getRealPath("/"));
    }

    public SecureRandom getRandom() {
        return random;
    }

    public void setRandom(SecureRandom random) {
        this.random = random;
    }

    public byte[] getFileContents() {
        return fileContents;
    }

    public void setFileContents(byte[] fileContents) {
        this.fileContents = fileContents;
    }

    public void uploadFile(FileUploadEvent event) {
        uploadedFile = event.getFile();
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }

    public void handleFileUpload(FileUploadEvent event) throws URISyntaxException {

        uploadedFile = event.getFile();
        if (uploadedFile != null) {
            String fileName = "P" + Util.getDateString() + "." + Util.getFileExtension(uploadedFile.getFileName());
            Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.LEAVE_REQUEST_ATTACHEMNT_UPLOAD_PATH));

            System.out.println("ROOT:" + rootRealPath);
            Path pathToSaveTo = rootRealPath.resolve(fileName);
            System.out.println("reslove" + pathToSaveTo);
            try (OutputStream strm = Files.newOutputStream(pathToSaveTo, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                strm.write(uploadedFile.getContents());

                //boolean currentTmpPhotoUploaded = selectedTmpEmployee.isTmpPhotoUploaded();
                // String currentTmpPhotoFileName = selectedTmpEmployee.getTmpPhotoFileName();
                //selectedTmpEmployee.setTmpPhotoUploaded(true);
                // selectedTmpEmployee.setTmpPhotoFileName(fileName);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getStackTrace());
                FacesMessage message = new FacesMessage("Upload Failed", "Upload failed. Please try again. ");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage("Upload Failed", "Upload failed. Please try again. ");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

  

    public void AttachLeaveFile(FileUploadEvent event)  {

        UploadedFile attachedFile = event.getFile();

        if (attachedFile != null) {

            try {
                secondPartExtension = getFileExtension(attachedFile.getFileName());
                
                finalImageContentBase64Encoded = getImageContentsAsBase64(attachedFile);

                //construct the image string form the uplpod content string
                previewImage = firstPartData.concat(secondPartExtension.concat(thirdPartComma).concat(finalImageContentBase64Encoded));

                System.out.println("---- Image bAse 64 encode ----");
                System.out.println(previewImage);
                System.out.println("---- Image bAse 64 encode ----");
                
                divStyle = "height:50%;width:100%;overflow: scroll;";

                FacesMessage message = new FacesMessage("Attach Leave Request File", "Upload successedd! You can change the image by uploading image again! ");
                FacesContext.getCurrentInstance().addMessage(null, message);

            } catch (Exception ex) {
                ex.printStackTrace();
                FacesMessage message = new FacesMessage("Upload Failed", "Upload failed. Please try again. ");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage("Upload Failed", "Upload failed. Please try again. ");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

//     public void upload(FileUploadEvent event) {
//        UploadedFile uploadedFile = event.getFile();
//        if (uploadedFile != null) {
//            //String empSerialID = (String) event.getComponent().getAttributes().get("employeeSerialID");
//            Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.PROFILE_PHOTO_PATH));
//            Path pathToSaveTo = rootRealPath.resolve(employeeSerialID + "_profile_photo." + getFileExtension(uploadedFile.getFileName()));
//            try (OutputStream strm = Files.newOutputStream(pathToSaveTo, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
//                strm.write(uploadedFile.getContents());
//            } catch (IOException ex) {
//                ex.printStackTrace();
//                FacesMessage message = new FacesMessage("Upload Failed", "Upload failed. Please try again. ");
//                FacesContext.getCurrentInstance().addMessage(null, message);
//            }
//            FacesMessage message = new FacesMessage("Upload Successful", uploadedFile.getFileName() + " is uploaded.");
//            FacesContext.getCurrentInstance().addMessage(null, message);
//        } else {
//            FacesMessage message = new FacesMessage("Upload Failed", "Upload failed. Please try again. ");
//            FacesContext.getCurrentInstance().addMessage(null, message);
//        }
//    }
    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    public String getImageContentsAsBase64(UploadedFile attachemnt) {
        fileContents = attachemnt.getContents();
        return Base64.getEncoder().encodeToString(fileContents);
    }

    public StreamedContent getImage() {
        if (uploadedFile == null) {
            return new DefaultStreamedContent();
        } else {
            return new DefaultStreamedContent(new ByteArrayInputStream(uploadedFile.getContents()), "image/png");
        }
    }
    
    public Blob Base64ToBytes(String imageString) throws IOException, SQLException {
        
        byte[] decodedValue = Base64.getDecoder().decode(imageString);  // Basic Base64 decoding        
         //String decodedByteArray =new String(decodedValue, StandardCharsets.UTF_8);
         Blob blob = new SerialBlob(decodedValue);
         
         return blob;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public String getDivStyle() {
        return divStyle;
    }

    public void setDivStyle(String divStyle) {
        this.divStyle = divStyle;
    }

   
    

    public static void main(String[] args) {

       // Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.LEAVE_REQUEST_ATTACHEMNT_UPLOAD_PATH));

        System.out.println("ROOT:");
        String fileName = "hello123.jpeg";
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
                    
            System.out.println(fileName.substring(fileName.lastIndexOf(".") + 1));
        
        }
    }
}
