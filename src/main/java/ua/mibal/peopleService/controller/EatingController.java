package ua.mibal.peopleService.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.mibal.peopleService.dao.EatingDao;
import ua.mibal.peopleService.model.Entry;

import javax.management.InstanceAlreadyExistsException;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@RestController
@RequestMapping("/eating")
public class EatingController {

    private final EatingDao eatingDao;

    public EatingController(EatingDao eatingDao) {
        this.eatingDao = eatingDao;
    }

    @PostMapping
    Entry addEntry(@RequestBody Entry entry) throws InstanceAlreadyExistsException {
        eatingDao.save(entry);
        return entry;
    }
}
