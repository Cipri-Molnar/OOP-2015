package models.animals;

import com.alexasapps.zoowsome.R;

public class Cow extends Mammal {

    /**
     * 'default' constructor for setting the attributes to some predefined
     * values
     */
    public Cow() {
        super(7.5, 0.5);
        setNrOfLegs(4);
        setNormalBodyTemp(37); // (? not sure how should I write this)
        // normalBodyTemp = 37;
        setName("Milka");
        percbodyHair = 100 / 100;

    }

    /**
     * constructor with arguments
     *
     * @param name
     */

    public Cow(String name, double maintenanceCost, double dangerPerc) {
        super(maintenanceCost, dangerPerc);
        setNrOfLegs(4);
        setNormalBodyTemp(37);
        setPercBodyTemp(100 / 100);
    }

    int image = R.drawable.milka;

    public int getImage() {
        return image;
    }
    /*
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Mammals.Cow);
	}
	*/
}
