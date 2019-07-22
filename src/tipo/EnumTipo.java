package tipo;

public enum EnumTipo {
    biologico("bio"),
    estructura("estr"),
    inmune("inm"),
    demonio("dem"),
    heroica("her"),
    gigante("gig"),
    pequeña("peq"),
    transportable("tp");

    private final String id;

    private EnumTipo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
