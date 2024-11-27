package com.example.application.views.test;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Test coll field")
@Route("")
@Menu(order = 1, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class TestCollectionFieldView extends VerticalLayout {

    public TestCollectionFieldView() {

        add(new CollectionField());
    }
}
