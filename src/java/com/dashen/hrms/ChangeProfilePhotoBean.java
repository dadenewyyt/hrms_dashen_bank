/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dashen.hrms;

import com.dashen.hrms.service.EmployeeService;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 *
 * @author MulugetaK
 */
@Component
@SpringViewScope
public class ChangeProfilePhotoBean {

    private String employeeSerialID;

    @Autowired
    EmployeeService empService;

    Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @PostConstruct
    public void init() {
        employeeSerialID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (employeeSerialID != null && !employeeSerialID.isEmpty()) {
            employee = empService.getByEmployeeSerialID(employeeSerialID);
        } else {
            //error
        }
    }

    public void upload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        if (uploadedFile != null) {
            //String empSerialID = (String) event.getComponent().getAttributes().get("employeeSerialID");
            Path rootRealPath = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath(Configuration.PROFILE_PHOTO_PATH));
            Path pathToSaveTo = rootRealPath.resolve(employeeSerialID + "_profile_photo." + getFileExtension(uploadedFile.getFileName()));
            try (OutputStream strm = Files.newOutputStream(pathToSaveTo, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                strm.write(uploadedFile.getContents());
            } catch (IOException ex) {
                ex.printStackTrace();
                FacesMessage message = new FacesMessage("Upload Failed", "Upload failed. Please try again. ");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
            FacesMessage message = new FacesMessage("Upload Successful", uploadedFile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage("Upload Failed", "Upload failed. Please try again. ");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }
}
