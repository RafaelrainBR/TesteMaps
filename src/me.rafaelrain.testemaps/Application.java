package me.rafaelrain.testemaps;

import me.rafaelrain.testemaps.exception.InsufficientBalanceException;
import me.rafaelrain.testemaps.model.Account;
import me.rafaelrain.testemaps.model.Asset;
import me.rafaelrain.testemaps.model.Position;
import me.rafaelrain.testemaps.service.MovementService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static me.rafaelrain.testemaps.util.TimeCounting.countTime;

public class Application {

    private final List<Asset> assetList = new ArrayList<>();
    private final MovementService movementService = new MovementService();
    private Account account;

    public static void main(String[] args) {
        Application app = new Application();
        app.start();
    }

    public void start() {
        createAccount();
        createAssets();
        doTransactions();
        getPositions();
    }

    public void createAccount() {
        this.account = countTime("Criando conta...", () -> new Account(50));
    }

    public void createAssets() {
        countTime("Criando ativos...", () -> assetList.addAll(
                Arrays.asList(
                        new Asset(
                                "Gasolina Aditivada",
                                6,
                                Asset.Type.RV
                        ),
                        new Asset(
                                "Dólar Americano",
                                5,
                                Asset.Type.RV
                        ),
                        new Asset(
                                "BOVESPA-SP",
                                3,
                                Asset.Type.FUNDO
                        )
                )
        ));
    }

    public void doTransactions() {
        countTime("Criando situações de transferencias...", () -> {

            print("Realizando a compra de 5 ativos de Gasolina Aditivada.");
            print("O saldo deverá ter o valor de 20.");
            movementService.buyAssets(account, assetList.get(0), 2, 30);
            print("SALDO: " + account.getBalance());
            print();

            print("Realizando a venda de 4 ativos de Gasolina Aditivada.");
            print("Um erro deverá ser chamado abaixo.");
            try {
                movementService.sellAssets(account, assetList.get(0), 4, 24);
                print("Nenhum erro foi chamado.");
            } catch (InsufficientBalanceException e) {
                print("Erro: " + e.getMessage());
            }
            print();

            print("Realizando a venda de 2 ativos de Gasolina Aditivada por um preço de 8 reais cada.");
            print("O saldo deverá ter o valor de 36.");
            movementService.sellAssets(account, assetList.get(0), 2, 16);
            print("SALDO: " + account.getBalance());
            print();

            print("Realizando a compra de 60 ativos de BOVESPA-SP");
            print("Um erro deverá ser chamado abaixo.");
            try {
                movementService.buyAssets(account, assetList.get(2), 60, 180);
                print("Nenhum erro foi chamado.");
            } catch (InsufficientBalanceException e) {
                print("Erro: " + e.getMessage());
            }
            print();

            print("Realizando a compra de 3 ativos de Dólar Americano.");
            print("O saldo deverá ter o valor de 21.");
            movementService.buyAssets(account, assetList.get(1), 3, 15);
            print("SALDO: " + account.getBalance());
            print();
        });
    }

    public void getPositions() {
        countTime("Carregando posições para cada ativo...", () -> {
            final List<Position> positions = assetList.stream()
                    .map(it -> Position.of(account, it))
                    .collect(Collectors.toList());

            for (int i = 0; i < positions.size(); i++) {
                print("POSITION " + (i + 1) + ":");
                print(positions.get(i).toString());
                print();
            }
        });
    }

    private void print(String text) {
        System.out.println(text);
    }

    private void print() {
        System.out.println();
    }

}
