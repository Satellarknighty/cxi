package de.unibremen.swp.matti.persistence;

import de.unibremen.swp.matti.models.Flashcard;
import de.unibremen.swp.matti.models.CardBox;
import de.unibremen.swp.matti.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Datastore {
    private Category currentCategory;
    private Flashcard currentFlashcard;
    private CardBox currentCardBox;
    private Category currentCategoryInCurrentCardBox;
}
