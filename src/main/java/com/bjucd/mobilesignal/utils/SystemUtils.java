package com.bjucd.mobilesignal.utils;

import com.bjucd.mobilesignal.repositoriies.config.SystemConfigRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by I015703 on 6/10/2019.
 */
@Service
public class SystemUtils {

    @Autowired
    SystemConfigRepository sysRepo;

    private static String version = "";

    public String getActiveVersion() {
        if (StringUtils.isBlank(version)) {
            synchronized (version) {
                if (sysRepo.existsById(1)) {
                    version = sysRepo.findById(1).get().getActiveVersion();
                }
            }
        }
        return version;
    }
}
