import React from "react";

import UserIcon from '../../assets/images/user-icon.svg';
import DataControl from '../../api/data_control';

import './styles.css';
import User from "../../models/user";

export default class Dashboard extends React.Component<{}, {user: User}> {
    async componentWillMount(){
        //const user = await DataControl.getUser(2);
        const user = new User({
            name: "Usuario",
            balance: 356.97,
            assets: [],
            transactions: []
        });
        
        this.state = {
            user: user
        }
    }
    render(){
        return (
            <div className="dashboard-box">
                <div className="user-info">
                    <img className="user-image"
                        src={UserIcon} alt="User Icon"/>

                    <h2 className="user-name">{this.state.user.name}</h2>
                </div>
                <div className="balance-container">
                    <p className="balance-text">Saldo: </p>
                    <h1 className="balance-value">R$ {this.state.user.balance}</h1>
                </div>
            </div>
        );
    }
}