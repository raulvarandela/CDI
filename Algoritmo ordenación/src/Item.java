public class Item {
    public String nombre;
    public int type; // asumo codigo tres digitos?
    public String descCastellano;


    @Override
    public String toString() {
        return String.format("%s: %s", nombre, descCastellano);
    }
}