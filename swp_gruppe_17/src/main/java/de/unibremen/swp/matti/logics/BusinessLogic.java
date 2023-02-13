package de.unibremen.swp.matti.logics;


import de.unibremen.swp.matti.models.*;
import de.unibremen.swp.matti.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class BusinessLogic {
    protected static final Datastore datastore;

    protected static final FlashcardRepository cardRepo;

    protected static final CategoryRepository categoryRepo;

    protected static final CardBoxRepository cardBoxRepo;

    static {
        datastore = new Datastore();
        cardRepo = new FlashcardRepository();
        categoryRepo = new CategoryRepository();
        cardBoxRepo = new CardBoxRepository();
    }

    public void startApplication() {
        PersistenceManager.getEntityManager().close();
    }
    public List<String> getCategoriesNames(){
        return categoryRepo.findAll().stream().map(Category::getName).toList();
    }
}
