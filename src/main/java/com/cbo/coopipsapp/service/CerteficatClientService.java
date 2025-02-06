package com.cbo.coopipsapp.service;


import com.cbo.coopipsapp.models.CerteficateInformation;
import com.cbo.coopipsapp.repository.CacheRepository;
import com.cbo.coopipsapp.repository.CertificateCacheRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Setter
@Slf4j
public class CerteficatClientService {

    private RestTemplate restTemplate;
    HttpHeaders headers;
    private final CacheRepository cacheRepository;
    private final CertificateCacheRepository certificateCacheRepository;
    @Value("${ets.ips.certificate.download.url}")
    private String certeficateDownloadUrl;

    @Autowired
    public CerteficatClientService(CacheRepository cacheRepository,
                                   CertificateCacheRepository certificateCacheRepository) {
        this.cacheRepository = cacheRepository;
        this.certificateCacheRepository = certificateCacheRepository;


    }

    public void create() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();

    }

    @CacheEvict(value = "certificates", allEntries = true)
    public void clearAllCache() {
        System.out.println("clearing all catche");
    }


    public CerteficateInformation downloadCerteficate(String serialNumber, CerteficateInformation certeficateInformation, String validToken) {
        /**
         * @// TODO: 8/14/2024  add certifciate cache mechanism using redis and uncomment the line
         */
        CerteficateInformation cachedCeretficate = null;
//        this.getFromCache(certeficateInformation.getCertificateSerialNumber());
        if (cachedCeretficate == null || !StringUtils.hasText(cachedCeretficate.getCertificate())) {
            create();
//         ContentType
            System.out.println("calling the certeficate api");
            headers.add("Content-type", "application/x-www-form-urlencoded");
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBearerAuth(validToken);
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<CerteficateInformation> responseEntity =
                    restTemplate.exchange(certeficateDownloadUrl + "?cert_iss=" +
                            certeficateInformation.getCertificateIssuer() + "&&cert_sn="
                            + certeficateInformation.getCertificateSerialNumber(), HttpMethod.GET, httpEntity, CerteficateInformation.class);
            certeficateInformation.setCertificate(responseEntity.getBody().getCertificate());
            /**
             * @// TODO: 8/14/2024  add certificate cache mechanism using redis
             */
//            cacheRepository.put(certeficateInformation.getCertificateSerialNumber(), certeficateInformation.getCertificate());
            System.out.println(responseEntity);
        } else {
            certeficateInformation.setCertificate(cachedCeretficate.getCertificate());
        }
        return certeficateInformation;

    }

//    @Cacheable(value = "certificates", key = "#serialNumber", unless = "#result == null || !#result.hasCertificate()")
//    public CerteficateInformation downloadCerteficate(String serialNumber, CerteficateInformation certificateInformation, String validToken) {
//        System.out.println("Cache miss: Fetching certificate from the API");
//
//        headers.add("Content-Type", "application/x-www-form-urlencoded");
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.setBearerAuth(validToken);
//
//        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
//
//        ResponseEntity<CerteficateInformation> responseEntity = restTemplate.exchange(
//                certificateDownloadUrl + "?cert_iss=" + certificateInformation.getCertificateIssuer()
//                        + "&&cert_sn=" + serialNumber,
//                HttpMethod.GET, httpEntity, CerteficateInformation.class);
//
//        if (responseEntity.getBody() != null) {
//            certificateInformation.setCertificate(responseEntity.getBody().getCertificate());
//        }
//
//        return certificateInformation;
//    }
    private CerteficateInformation getFromCache(String serialNumber) {
        Optional<String> s = cacheRepository.get(serialNumber);
        CerteficateInformation certeficateInformation = null;
        if (s.isPresent()) {
            log.debug("Found the key in cache {} ", s.get());
            certeficateInformation = new CerteficateInformation();
            certeficateInformation.setCertificate(s.get());
        }
        return certeficateInformation;
    }
}
