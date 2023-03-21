package ru.job4j.cars.service;

import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Advert;

import java.io.IOException;
import java.util.List;

public interface AdvertService {

    List<Advert> findAllAdverts();

    Advert save(Advert advert, MultipartFile file) throws IOException;

    Advert findAdvertById(Integer advertId);

    List<Advert> findAdvertsByUserId(int id);

    void deleteAdvertById(Integer advertId);

    Advert update(Advert advert);

    List<Advert> filterAdverts(String bodyType, String transmissionname,
                                      String drive, String fuel);
}
