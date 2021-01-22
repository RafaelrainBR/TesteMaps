package me.rafaelrain.testemaps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TesteMapsApplication {

    //TODO: Consulta de posição: Uma lista de registros para cada ativo
    //TODO: Nome do ativo, tipo, quantidade total, valor de mercado total, rendimento.
    //TODO:   Quantidade total: soma das quantidades compradas menos as quantidades vendidas do ativo
    //TODO:   Valor de mercado total: quantidade total multiplicada pelo preço de mercado do ativo
    //TODO:   Rendimento: preço de mercado dividido pelo preço médio das compras
    //TODO:   Lucro: soma dos valores das vendas menos os valores das compras do ativo

    public static void main(String[] args) {
        SpringApplication.run(TesteMapsApplication.class, args);
    }

}
