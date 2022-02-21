package com.training.api.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.training.api.entity.UserEntity;
import com.training.api.global.GlobalConstant;
import com.training.api.global.GlobalFunction;
import com.training.api.model.ResponseModel;
import com.training.api.model.UserModel;
import com.training.api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements Serializable{

    @Autowired
    UserRepository userRepository;

    public String getAllUser(){
        ResponseModel responseModel = new ResponseModel();

        try {
			List<UserEntity> users = new ArrayList<UserEntity>();
            userRepository.findAll().forEach(users::add);
            
            responseModel.setResponseCode(GlobalConstant.SUCCESS_CODE);
            responseModel.setResponseMessage("Success");
            responseModel.setResponseData(users);
		} catch (Exception e) {
            responseModel.setResponseCode(GlobalConstant.INTERNAL_ERROR_CODE);
            responseModel.setResponseMessage(e.getMessage());
            responseModel.setResponseDescription(e.getMessage());
            e.printStackTrace();
		}

        return GlobalFunction.returnResponse(responseModel);
    }

    public String findById(UserModel user){
        ResponseModel responseModel = new ResponseModel();

        try {
            UserEntity _user = userRepository.findByUserId(user.getUserId());

            if(_user==null){
                responseModel.setResponseCode(GlobalConstant.INTERNAL_ERROR_CODE);
                responseModel.setResponseMessage("No Data");
                responseModel.setResponseDescription("Data Not Found");
            }else{
                responseModel.setResponseCode(GlobalConstant.SUCCESS_CODE);
                responseModel.setResponseMessage("Success");
                responseModel.setResponseData(_user);
            }
        } catch (Exception e) {
            responseModel.setResponseCode(GlobalConstant.INTERNAL_ERROR_CODE);
            responseModel.setResponseMessage(e.getMessage());
            responseModel.setResponseDescription(e.getMessage());
            e.printStackTrace();
        }

        return GlobalFunction.returnResponse(responseModel);
    }

    public String AddUser(UserModel user){
        ResponseModel responseModel = new ResponseModel();

        try {
            ArrayList<String> error = new ArrayList<String>();

            if (user.getUserName()==null||user.getUserName().isEmpty()){
                error.add("User Name is Required");
            } else if (userRepository.countNameUsed(user.getUserName())>0){
                error.add("User Name is Already Used");
            }
            if (user.getCreatedBy()==null||user.getCreatedBy()<=0){
                error.add("Created By greater than 0");
            }
            
            if (error.size()>0){
                responseModel.setResponseCode(GlobalConstant.EXTERNAL_ERROR_CODE);
				responseModel.setResponseMessage("User input wrong value...");
				responseModel.setResponseDescription(error.toString());
				return GlobalFunction.returnResponse(responseModel);
            }

            UserEntity _user = new UserEntity();
            _user.setUserName(user.getUserName());
            _user.setCreatedBy(user.getCreatedBy());
            _user.setCreatedDate(new Timestamp(new Date().getTime()));
            _user.setRecStatus(GlobalConstant.REC_STATUS_ACTIVE);

            _user = userRepository.save(_user);

            responseModel.setResponseCode(GlobalConstant.SUCCESS_CODE);
			responseModel.setResponseMessage("Successfully Create New User");
			responseModel.setResponseData(_user);

        } catch (Exception e) {
            responseModel.setResponseCode(GlobalConstant.INTERNAL_ERROR_CODE);
			responseModel.setResponseMessage(e.getMessage());
			responseModel.setResponseDescription(e.getMessage());
			e.printStackTrace();
        }

        return GlobalFunction.returnResponse(responseModel);
    }

    public String updateUser(UserModel user){
        ResponseModel responseModel = new ResponseModel();

        try {
            ArrayList<String> error = new ArrayList<String>();

            if (user.getUserId()==null){
                error.add("User Id is Required");
            }
            if (user.getUserName()==null||user.getUserName().isEmpty()){
                error.add("User Name is Required");
            } else if (userRepository.countNameUsedExclude(user.getUserName(),user.getUserId())>0){
                error.add("User Name is Already Used");
            }
            if (user.getUpdatedBy()==null){
                error.add("Updated By is Required");
            }
            
            if (error.size()>0){
                responseModel.setResponseCode(GlobalConstant.EXTERNAL_ERROR_CODE);
				responseModel.setResponseMessage("User input wrong value...");
				responseModel.setResponseDescription(error.toString());
				return GlobalFunction.returnResponse(responseModel);
            }

            UserEntity _user = new UserEntity();
            _user = userRepository.findByUserId(user.getUserId());

            if (_user==null) throw new Exception("User id ("  + user.getUserId() + ") not found");
            if (_user.getRecStatus().equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
				throw new Exception("User id ("  + user.getUserId() + ") is already been deleted.");
			}

            _user.setUserName(user.getUserName());
            _user.setUpdatedBy(user.getUpdatedBy());
            _user.setUpdatedDate(new java.sql.Date(new Date().getTime()));

            _user = userRepository.save(_user);

            responseModel.setResponseCode(GlobalConstant.SUCCESS_CODE);
			responseModel.setResponseMessage("Successfully Update User : " + _user.getUserId());
			responseModel.setResponseData(_user);

        } catch (Exception e) {
            responseModel.setResponseCode(GlobalConstant.INTERNAL_ERROR_CODE);
			responseModel.setResponseMessage(e.getMessage());
			responseModel.setResponseDescription(e.getMessage());
			e.printStackTrace();
        }

        return GlobalFunction.returnResponse(responseModel);
    }

    public String deleteUser(UserModel user){
        ResponseModel responseModel = new ResponseModel();

        try {
            ArrayList<String> error = new ArrayList<String>();

            if (user.getUserId()==null){
                error.add("User Id is Required");
            } else if (userRepository.countIdUsed(user.getUserId())<=0){
                error.add("User Id Not Found");
            }
            
            if (error.size()>0){
                responseModel.setResponseCode(GlobalConstant.EXTERNAL_ERROR_CODE);
				responseModel.setResponseMessage("User input wrong value...");
				responseModel.setResponseDescription(error.toString());
				return GlobalFunction.returnResponse(responseModel);
            }

            UserEntity _user = new UserEntity();
            _user.setDeletedDate(new java.sql.Date(new Date().getTime()));
            _user.setRecStatus(GlobalConstant.REC_STATUS_NON_ACTIVE);

            _user = userRepository.save(_user);

            responseModel.setResponseCode(GlobalConstant.SUCCESS_CODE);
			responseModel.setResponseMessage("Successfully Delete User : " + _user.getUserId());
			responseModel.setResponseData(_user);

        } catch (Exception e) {
            responseModel.setResponseCode(GlobalConstant.INTERNAL_ERROR_CODE);
			responseModel.setResponseMessage(e.getMessage());
			responseModel.setResponseDescription(e.getMessage());
			e.printStackTrace();
        }

        return GlobalFunction.returnResponse(responseModel);
    }
}
