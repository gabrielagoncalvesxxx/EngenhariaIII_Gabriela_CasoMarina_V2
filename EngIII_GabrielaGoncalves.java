import java.util.ArrayList;
import java.util.List;

interface DespesaCalculavel {
    double calcularValor(int totalQuartos, int quartosApartamento);
}

abstract class Despesa implements DespesaCalculavel {
    protected String referencia;
    protected double valor;

    public Despesa(String referencia, double valor) {
        this.referencia = referencia;
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }
}

class DespesaFixa extends Despesa {
    public DespesaFixa(String referencia, double valor) {
        super(referencia, valor);
    }

    @Override
    public double calcularValor(int totalQuartos, int quartosApartamento) {
        return (valor / totalQuartos) * quartosApartamento;
    }
}

// Classe concreta para despesas variáveis
class DespesaVariavel extends Despesa {
    public DespesaVariavel(String referencia, double valor) {
        super(referencia, valor);
    }

    @Override
    public double calcularValor(int totalQuartos, int quartosApartamento) {
        return (valor / totalQuartos) * quartosApartamento;
    }
}

class Apartamento {
    private int numero;
    private int quartos;
    private String tipoOcupacao;
    private String nomeProprietario;
    private String telefoneProprietario;
    private List<Despesa> despesas;

    public Apartamento(int numero, int quartos, String tipoOcupacao, String nomeProprietario, String telefoneProprietario) {
        this.numero = numero;
        this.quartos = quartos;
        this.tipoOcupacao = tipoOcupacao;
        this.nomeProprietario = nomeProprietario;
        this.telefoneProprietario = telefoneProprietario;
        this.despesas = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public int getQuartos() {
        return quartos;
    }

    public void adicionarDespesa(Despesa despesa) {
        despesas.add(despesa);
    }

    public double calcularCondominio(int totalQuartos) {
        double totalDespesas = 0.0;
        for (Despesa despesa : despesas) {
            totalDespesas += despesa.calcularValor(totalQuartos, quartos);
        }
        return totalDespesas;
    }
}

class Condominio {
    private List<Apartamento> apartamentos;
    private List<Despesa> despesasComuns;
    private double multa;
    private boolean multaNoProximoMes;

    public Condominio() {
        this.apartamentos = new ArrayList<>();
        this.despesasComuns = new ArrayList<>();
        this.multa = 0.02; // Multa inicial de 2%
        this.multaNoProximoMes = false;
    }

    public void adicionarApartamento(Apartamento apartamento) {
        apartamentos.add(apartamento);
    }

    public void adicionarDespesaComum(Despesa despesa) {
        despesasComuns.add(despesa);
    }

    public void pagarCondominio(String referencia, double valorPago) {
        for (Apartamento apartamento : apartamentos) {
            double totalCondominio = apartamento.calcularCondominio(totalQuartos());
            if (multaNoProximoMes) {
                totalCondominio += totalCondominio * multa;
                multaNoProximoMes = false;
            }
            if (valorPago < totalCondominio) {
                multaNoProximoMes = true;
                System.out.println("Multa aplicada para o mês de " + referencia);
            }
        }
    }

    private int totalQuartos() {
        int total = 0;
        for (Apartamento apartamento : apartamentos) {
            total += apartamento.getQuartos();
        }
        return total;
    }
}

public class CondominioApp {
    public static void main(String[] args) {
        Apartamento apto101 = new Apartamento(101, 2, "proprietário", "Juan", "123456789");
        apto101.adicionarDespesa(new DespesaFixa("Março/2023", 200.00));

        Apartamento apto201 = new Apartamento(201, 2, "proprietário", "Bia", "987654321");
        apto201.adicionarDespesa(new DespesaFixa("Março/2023", 200.00));

        Apartamento apto202 = new Apartamento(202, 3, "inquilino", "Gabi", "555555555");
        apto202.adicionarDespesa(new DespesaVariavel("Março/2023", 200.00));

        Condominio condominio = new Condominio();
        condominio.adicionarApartamento(apto101);
        condominio.adicionarApartamento(apto201);
        condominio.adicionarApartamento(apto202);
        condominio.adicionarDespesaComum(new DespesaVariavel("Março/2023", 1000.00));

        condominio.pagarCondominio("Março/2023", 600.00);
    }
}
