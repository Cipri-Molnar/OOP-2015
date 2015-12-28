package com.example.maria.zoowsome.models.animals;

import com.example.maria.zoowsome.R;

import java.util.Calendar;
import java.util.TimeZone;

public class Jellyfish extends Aquatic {

    private int image = R.drawable.jellyfish;

    public Jellyfish(double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(0);
        setName("Sting");
        setAvgSwimDepth(150);
        setIsDangerous(true);
    }

    public Jellyfish(String name, int swimDepth, double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(0);
        setName(name);
        setAvgSwimDepth(swimDepth);
        setIsDangerous(true);
    }

    public double getPredisposition() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if ((dayOfWeek == 1) || (dayOfWeek == 4)) {
            return 0.25;
        }
        return 0;
    }

    public int getImage() {
        return image;
    }

//    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
//        super.encodeToXml(eventWriter);
//        createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Aquatics.Jellyfish);
//    }
}
