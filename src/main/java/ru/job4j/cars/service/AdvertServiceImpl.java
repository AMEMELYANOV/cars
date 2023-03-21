package ru.job4j.cars.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Advert;
import ru.job4j.cars.repository.AdRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class AdvertServiceImpl implements AdvertService {

    @Value("${upload.path}")
    private String uploadPath;
    private final AdRepository adRepository;

    public AdvertServiceImpl(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    @Override
    public List<Advert> findAllAdverts() {
        return adRepository.findAllAdverts();
    }

    @Override
    public Advert save(Advert advert, MultipartFile file) throws IOException {
        if (!file.isEmpty() && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            advert.setFilename(resultFilename);
        }
        return advert.getId() == 0 ? adRepository.save(advert) : adRepository.update(advert);
    }

    @Override
    public Advert findAdvertById(Integer advertId) {
        return adRepository.findAdvertById(advertId);
    }

    @Override
    public List<Advert> findAdvertsByUserId(int id) {
        return adRepository.findAdvertsByUserId(id);
    }

    @Override
    public void deleteAdvertById(Integer advertId) {
        adRepository.deleteAdvertById(advertId);
    }

    @Override
    public Advert update(Advert advert) {
        return adRepository.update(advert);
    }

    @Override
    public List<Advert> filterAdverts(String bodyType, String transmissionname,
                                      String drive, String fuel) {
        return adRepository.filterAdverts(bodyType, transmissionname, drive, fuel);
    }
}
