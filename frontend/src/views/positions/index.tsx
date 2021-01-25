import React from "react";

import Position from "../../models/position";

import './styles.css';

function sellPosition(position: Position){
    const input = prompt(position.asset.name);
    if(!input) return;

    const amount = Number.parseInt(input);
    if(amount > position.amount){
        alert("Você não tem ações suficientes para vender.");
        return;
    }


}

export default class Positions extends React.Component<{}, {list: Array<Position>}> {
    componentWillMount(){
        
    }
    render(){
        this.updatePositions();
        return (
            <div className="positions-box">
                <div className="position-table">
                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Quantidade</th>
                            <th>Preço de Mercado</th>
                            <th>Rendimento</th>
                            <th>Lucro</th>
                            <th>Opções</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.list.map(function(position: Position){
                            return (
                                <tr key={position.asset.name}>
                                    <td>{position.asset.name}</td>
                                    <td>{position.amount} Ações</td>
                                    <td>R$ {position.marketPrice}</td>
                                    <td>R$ {position.income}</td>
                                    <td>R$ {position.gain}</td>
                                    <td className="row-button">
                                        <a onClick={() => sellPosition(position)}>Vender</a>
                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </div>
            </div>
        );
    }
    updatePositions(){
        this.state = {
            list: [
                {
                    asset: {
                        name: "asset exemplo",
                        marketPrice: 648,
                        type: "RF"
                    },
                    amount: 32,
                    marketPrice:4651,
                    income: 169851,
                    gain:16321
                },
                {
                    asset: {
                        name: "asset loko",
                        marketPrice: 648,
                        type: "RF"
                    },
                    amount: 32,
                    marketPrice:4651,
                    income: 169851,
                    gain:16321
                },
                {
                    asset: {
                        name: "asset loko",
                        marketPrice: 648,
                        type: "RF"
                    },
                    amount: 32,
                    marketPrice:4651,
                    income: 169851,
                    gain:16321
                }
            ]
        };
    }
}