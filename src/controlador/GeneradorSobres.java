package controlador;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import persistencias.Carta;

public class GeneradorSobres {

    private ArrayList<Carta> cartas;
    private int contadorSobres;

    public GeneradorSobres(ArrayList<Carta> cartas) {
        this.cartas = cartas;
        this.contadorSobres = 0;
    }

    public Sobre generarSobre() {
        contadorSobres++;
        Sobre sobre = new Sobre(contadorSobres);

        ArrayList<Carta> comunes = filtrarCartasPorRareza("common");
        ArrayList<Carta> infrecuentes = filtrarCartasPorRareza("uncommon");
        ArrayList<Carta> rarasOMiticas = filtrarCartasPorRareza("rare", "mythic");

        for (int i = 0; i < 10; i++) {
            sobre.agregarCarta(seleccionarCartaPorProbabilidad(comunes));
        }

        for (int i = 0; i < 3; i++) {
            sobre.agregarCarta(seleccionarCartaPorProbabilidad(infrecuentes));
        }

        sobre.agregarCarta(seleccionarCartaPorProbabilidad(rarasOMiticas));

        sobre.setEsFinSobre(sobre.getCartasSobre().size() == 14);

        return sobre;
    }

    private ArrayList<Carta> filtrarCartasPorRareza(String... rarezas) {
        ArrayList<String> rarezasList = new ArrayList<>();
        for (String rareza : rarezas) {
            rarezasList.add(rareza);
        }
        return cartas.stream()
                .filter(carta -> rarezasList.contains(carta.getRareza()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Carta seleccionarCartaPorProbabilidad(ArrayList<Carta> cartas) {
        int totalSeen = cartas.stream().mapToInt(Carta::getSeen).sum();
        int random = new Random().nextInt(totalSeen);
        int cumulative = 0;

        for (Carta carta : cartas) {
            cumulative += carta.getSeen();
            if (cumulative > random) {
                return carta;
            }
        }

        // controlar,  devolvemos la ultima carta
        return cartas.get(cartas.size() - 1);
    }
}
