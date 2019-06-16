package com.bjucd.mobilesignal.controllers;

import com.bjucd.mobilesignal.repositoriies.config.SystemConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by I015703 on 6/12/2019.
 */
@Controller
@CrossOrigin
@RequestMapping("/region")
public class RegionController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SystemConfigRepository sysRepo;



}
