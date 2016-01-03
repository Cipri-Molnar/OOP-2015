package models.animals;

import com.alexasapps.zoowsome.R;

public class Butterfly extends Insect {

    public Butterfly() {
        super(4.5, 0.3);
        setName("Hesperiidae");
        setNrOfLegs(12); // I have no idea
        setCanFly(true);
        setDangerous(false);
    }

    public Butterfly(String name, boolean canFly, int nrOfLegs, boolean isDangerous, double dangerPerc,
                     double maintenanceCost) {
        super(maintenanceCost, dangerPerc);
        setName(name);
        setNrOfLegs(nrOfLegs);
        setCanFly(canFly);
        setDangerous(isDangerous);
    }


    int image = R.drawable.butt;

    public int getImage() {
        return image;
    }

	/*
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Insects.Butterfly);
	}
	*/
}
