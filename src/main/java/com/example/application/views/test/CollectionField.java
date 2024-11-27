package com.example.application.views.test;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;
import java.util.List;

public class CollectionField extends CustomField<List<String>> {

    private final VerticalLayout rows;

    private final List<TextField> fields = new ArrayList<>();
    private final List<Button> removeButtons = new ArrayList<>();

    /**
     * Constructor
     */
    public CollectionField() {

        rows = new VerticalLayout();

        add(
                new Button("View values", this::onClickPrint),
                new Button("Add text record", this::onClickAdd),
                rows);
    }

    /**
     * Prints the values of the fields
     * @param event the click event
     */
    private void onClickPrint(ClickEvent<Button> event) {
        StringBuilder sb = new StringBuilder();
        for(TextField field : fields) {
            String value = field.getValue();
            if(value == null)
                sb.append("<null>");
            else
                sb.append("<").append(value.isEmpty()?"empty":value).append(">");
            sb.append(",");
        }
        String val = sb.isEmpty() ? "No field is displayed" :sb.substring(0, sb.length()-1);

        Notification.show(val).addThemeVariants(NotificationVariant.LUMO_WARNING);
    }

    /**
     * Add a new row to the collection
     * @param event the click event
     */
    public void onClickAdd(ClickEvent<Button> event)    {

        final int indexOfElement = removeButtons.size();

        // Build the UI
        TextField field = new TextField();
        fields.add(field);

        Button removeBtn = new Button(VaadinIcon.TRASH.create());
        removeBtn.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
        removeBtn.getElement().setAttribute("data-index", String.valueOf(indexOfElement));
        removeBtn.addClickListener(this::onClickRemove);
        removeButtons.add(removeBtn);

        // Add the new row to the layout
        rows.add(new HorizontalLayout(field, removeBtn));
    }


    /**
     * Get the data-index attribute from a component
     * @param c the component
     * @return the associated index
     */
    private int getDataIndexAttr(Component c){
        return Integer.parseInt((c).getElement().getAttribute("data-index"));
    }

    /**
     * Removes a row
     * @param event the click event
     */
    public void onClickRemove(ClickEvent<Button> event)    {
        int index = getDataIndexAttr(event.getSource());

        // remove from the inner collections
        fields.remove(index);
        removeButtons.remove(index);

        // remove from the UI
        int i=0;
        for(Component c : rows.getChildren().toList()){
            if(i == index){
                rows.remove(c);
                break;
            }
            i++;
        }

        // Refresh the remaining buttons
        updateDataIndexes();
    }

    /**
     * Update the data-index attribute on the remaining buttons
     */
    public void updateDataIndexes(){
        for(int i=0; i < removeButtons.size(); i++){
            removeButtons.get(i).getElement().setAttribute("data-index", String.valueOf(i));
        }
    }

    @Override
    protected List<String> generateModelValue() {
        return List.of();
    }

    @Override
    protected void setPresentationValue(List<String> newPresentationValue) {

    }
}
