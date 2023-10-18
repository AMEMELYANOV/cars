package ru.job4j.cars.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.UserReadDto;
import ru.job4j.cars.model.Advert;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.AdvertRepository;
import ru.job4j.cars.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Реализация сервиса по работе с объявлениями
 * @see ru.job4j.cars.service.AdvertService
 * @author Alexander Emelyanov
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class AdvertServiceImpl implements AdvertService {

    /**
     * Абсолютный путь к папке для хранения изображений указывается
     * в конфигурационном файле application.properties
     */
    @Value("${upload.path}")
    private String uploadPath;

    /**
     * Объект для доступа к методам AdvertRepository
     */
    private final AdvertRepository advertRepository;

    /**
     * Объект для доступа к методам UserRepository
     */
    private final UserRepository userRepository;

    /**
     * Выполняет возврат всех объявлений.
     *
     * @return список объявлений
     */
    @Override
    public List<Advert> findAllAdverts() {
        return advertRepository.findAllAdverts();
    }

    /**
     * Выполняет сохранение объявления. Возвращает
     * объявления с проинициализированным идентификатором.
     * Если при копировании файла изображения автомобиля происходят
     * ошибки, будет выброшено IOException.
     *
     * @param advert объявление
     * @param file файл изображения
     * @param userDto объект DTO пользователя
     * @return advert объявление с проинициализированным идентификатором
     * @exception IOException если при сохранении файла изображения произошли ошибки
     */
    @Override
    public Advert save(Advert advert, MultipartFile file, UserReadDto userDto) throws IOException {
        User userFromDB = userRepository.findUserByEmail(userDto.getEmail());
        advert.setUser(userFromDB);
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
        return advert.getId() == 0 ? advertRepository.save(advert)
                : advertRepository.update(advert);
    }

    /**
     * Выполняет поиск и возврат объявления по идентификатору.
     *
     * @param advertId идентификатор
     * @return advert объявление
     */
    @Override
    public Advert findAdvertById(Integer advertId) {
        return advertRepository.findAdvertById(advertId);
    }

    /**
     * Выполняет поиск и возврат объявления по идентификатору пользователя.
     *
     * @param id идентификатор
     * @return advert объявление
     */
    @Override
    public List<Advert> findAdvertsByUserId(int id) {
        return advertRepository.findAdvertsByUserId(id);
    }

    /**
     * Выполняет удаление объявления по идентификатору.
     *
     * @param advertId идентификатор
     */
    @Override
    public void deleteAdvertById(Integer advertId) {
        advertRepository.deleteAdvertById(advertId);
    }

    /**
     * Выполняет обновление объявления. Возвращает
     * обновленное объявление.
     *
     * @param advert объявление
     * @return advert обновленное объявление
     */
    @Override
    public Advert update(Advert advert) {
        return advertRepository.update(advert);
    }

    /**
     * Выполняет фильтрацию объявлений по переданным параметрам.
     * Вернет список объявлений соответствующих фильтру.
     *
     * @param bodyType тип кузова
     * @param transmissionname трансмиссия
     * @param drive тип привода
     * @param fuel тип топлива
     * @return список объявлений
     */
    @Override
    public List<Advert> filterAdverts(String bodyType, String transmissionname,
                                      String drive, String fuel) {
        return advertRepository.filterAdverts(bodyType, transmissionname, drive, fuel);
    }
}
