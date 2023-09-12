package com.example.projcrech;

import java.util.ArrayList;
import java.util.List;

public class StatusLunchSnackData {

    public static List<StatusLunchSnack> getStatusList() {
        List<StatusLunchSnack> statusList = new ArrayList<>();

        StatusLunchSnack Good = new StatusLunchSnack();
        Good.setName("Comeu tudo");
        Good.setImage(R.drawable.ic_satisfied_pressed);
        statusList.add(Good);

        StatusLunchSnack Medium = new StatusLunchSnack();
        Medium.setName("Comeu pouco");
        Medium.setImage(R.drawable.ic_neutral_pressed);
        statusList.add(Medium);

        StatusLunchSnack Bad = new StatusLunchSnack();
        Bad.setName("Não comeu");
        Bad.setImage(R.drawable.ic_dissatisfied_pressed);
        statusList.add(Bad);

        return statusList;
    }

}
