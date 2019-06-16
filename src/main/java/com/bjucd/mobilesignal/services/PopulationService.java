package com.bjucd.mobilesignal.services;

import com.bjucd.mobilesignal.models.base.CityCoord;
import com.bjucd.mobilesignal.models.base.ProvinceCoord;
import com.bjucd.mobilesignal.models.population.MigrationInChina;
import com.bjucd.mobilesignal.models.population.MigrationInProvince;
import com.bjucd.mobilesignal.models.population.Population;
import com.bjucd.mobilesignal.models.responseBody.MigrationResponse;
import com.bjucd.mobilesignal.models.responseBody.NameValue;
import com.bjucd.mobilesignal.repositoriies.base.CityCoordRepository;
import com.bjucd.mobilesignal.repositoriies.base.DistrictCoordRepository;
import com.bjucd.mobilesignal.repositoriies.base.ProvinceCoordRepository;
import com.bjucd.mobilesignal.repositoriies.population.MigrationInChinaRepository;
import com.bjucd.mobilesignal.repositoriies.population.MigrationInProvinceRepository;
import com.bjucd.mobilesignal.repositoriies.population.PopulationRepository;
import com.bjucd.mobilesignal.utils.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by I015703 on 6/11/2019.
 */
@Service
public class PopulationService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SystemUtils systemUtil;

    @Autowired
    PopulationRepository popRepo;

    @Autowired
    MigrationInChinaRepository interMigRepo;

    @Autowired
    MigrationInProvinceRepository intraMigRepo;

    @Autowired
    ProvinceCoordRepository provinceCoordRepo;

    @Autowired
    CityCoordRepository cityCoordRepo;

    public List<NameValue<Double[]>> getPopulationDensity(String city, boolean isCu) {
        String version = systemUtil.getActiveVersion();
        List<Population> populations = popRepo.findByCityAndVersion(city, version);
        return populations.stream().map(pop ->
                new NameValue<Double[]>(pop.getDistrict(), new Double[]{
                        (isCu ? pop.getCuResident() : pop.getKyResident()) / pop.getArea(),
                        (isCu ? pop.getCuEmployment() : pop.getKyEmployment()) / pop.getArea(),
                        (isCu ? pop.getCuTemporary() : pop.getKyTemporary()) / pop.getArea()
                })).collect(Collectors.toList());
    }

    public List<NameValue<Integer[]>> getPopulationAmount(String city, boolean isCu) {
        String version = systemUtil.getActiveVersion();
        List<Population> populations = popRepo.findByCityAndVersion(city, version);
        return populations.stream().map(pop ->
                new NameValue<Integer[]>(pop.getDistrict(), new Integer[]{
                        isCu ? pop.getCuResident() : pop.getKyResident(),
                        isCu ? pop.getCuEmployment() : pop.getKyEmployment(),
                        isCu ? pop.getCuTemporary() : pop.getKyTemporary()
                })).collect(Collectors.toList());
    }

    public List<MigrationResponse> getIntraProvinceMigration(String city, boolean isCu) {
        String version = systemUtil.getActiveVersion();
        List<MigrationInProvince> migs = intraMigRepo.findByCityAndVersion(city, version);
        List<String> sourceCities = migs.stream().map(mig -> mig.getFromCity()).collect(Collectors.toList());
        sourceCities.add(city);
        List<CityCoord> cityCoords = cityCoordRepo.findByVersionAndCityIn(version, sourceCities);
        Map<String, Double[]> cityCoordsMap = new HashMap<>();
        cityCoords.parallelStream().forEach(cityCoord ->
                cityCoordsMap.put(cityCoord.getCity(), new Double[]{cityCoord.getLng(), cityCoord.getLat()}));
        return migs.parallelStream().map(mig -> MigrationResponse.builder()
                .amount(isCu ? mig.getCuFloatPop() : mig.getKyFloatPop())
                .sourceName(mig.getFromCity()).sourceCoord(cityCoordsMap.get(mig.getFromCity()))
                .targetName(mig.getCity()).targetCoord(cityCoordsMap.get(mig.getCity())).build())
                .sorted(Comparator.comparing(MigrationResponse::getAmount).reversed())
                .collect(Collectors.toList());
    }

    public List<MigrationResponse> getInterProvinceMigration(String city, boolean isCu) {
        String version = systemUtil.getActiveVersion();
        List<MigrationInChina> migs = interMigRepo.findByCityAndVersion(city, version);
        List<String> sourceProvinces = migs.stream().map(mig -> parseProvinceName(mig.getFromProvince())).collect(Collectors.toList());
        List<ProvinceCoord> provinceCoords = provinceCoordRepo.findByVersionAndProvinceIn(version, sourceProvinces);
        Map<String, Double[]> provinceCoordsMap = new HashMap<>();
        provinceCoords.parallelStream().forEach(coord ->
                provinceCoordsMap.put(coord.getProvince(), new Double[]{coord.getLng(), coord.getLat()}));
        logger.info("{}", provinceCoordsMap);
        CityCoord cityCoord = cityCoordRepo.findOneByCityAndVersion(city, version);

        return migs.parallelStream().map(mig -> MigrationResponse.builder()
                .amount(isCu ? mig.getCuFloatPop() : mig.getKyFloatPop())
                .sourceName(mig.getFromProvince()).sourceCoord(provinceCoordsMap.get(parseProvinceName(mig.getFromProvince())))
                .targetName(mig.getCity()).targetCoord(new Double[]{cityCoord.getLng(), cityCoord.getLat()}).build())
                .sorted(Comparator.comparing(MigrationResponse::getAmount).reversed())
                .collect(Collectors.toList());
    }

    private String parseProvinceName(String name) {
        if (name.endsWith("市") || name.endsWith("省") || name.endsWith("区"))
            return name;
        else if (name.startsWith("北京") || name.startsWith("天津") || name.startsWith("上海") || name.startsWith("重庆"))
            return name + "市";
        else if (name.startsWith("内蒙古") ||  name.startsWith("西藏"))
            return name + "自治区";
        else if (name.startsWith("新疆"))
            return name + "维吾尔自治区";
        else if (name.startsWith("广西"))
            return name + "壮族自治区";
        else if (name.startsWith("宁夏"))
            return name + "回族自治区";
        else
            return name + "省";
    }

}
