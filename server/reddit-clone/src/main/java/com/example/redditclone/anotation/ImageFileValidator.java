package com.example.redditclone.anotation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageFileValidator implements ConstraintValidator<ValidImage, MultipartFile> {


    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        boolean result = true;

        String contentType = value.getContentType();
        if(!isSupportedContentType(contentType)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Only png or jpg images are allowed").addConstraintViolation();
            result = false;
        }
        return  result;

    }


    private boolean isSupportedContentType(String contentType){
        return contentType.equals("image/png") ||
                contentType.equals("image.jpeg") ||
                contentType.equals("image/jpeg");
    }
}
