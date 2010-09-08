package com.vaadin.addon.chameleon;

import java.util.Iterator;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class Segment extends HorizontalLayout {

    public Segment() {
        addStyleName("segment");
    }

    public Segment addButton(Button b) {
        addComponent(b);
        b.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                if (event.getButton().getStyleName().indexOf("down") == -1) {
                    event.getButton().addStyleName("down");
                } else {
                    event.getButton().removeStyleName("down");
                }
            }
        });
        updateButtonStyles();
        return this;
    }

    private void updateButtonStyles() {
        int i = 0;
        Component c = null;
        for (Iterator<Component> iterator = getComponentIterator(); iterator
                .hasNext();) {
            c = iterator.next();
            c.removeStyleName("first");
            c.removeStyleName("last");
            if (i == 0) {
                c.addStyleName("first");
            }
            i++;
        }
        if (c != null) {
            c.addStyleName("last");
        }
    }
}
