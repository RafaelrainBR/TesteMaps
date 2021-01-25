import React from "react";
import { Link } from "react-router-dom";

import './styles.css';

export default class Header extends React.Component {
    render(){
        return (<>
            <header>
                <div className="header-container">
                    <h1 className="title">Maps Ações</h1>
                    <div className="menu">
                        <ul>
                            <li><Link className="link" to="/">Pagina Inicial</Link></li>
                            <li><Link className="link" to="/position">Suas Ações</Link></li>
                            <li><Link className="link" to="/assets">Cartela de Ações</Link></li>
                        </ul>
                    </div>
                </div>
            </header>
            <div className="page-content">
                {this.props.children}
            </div>
        </>);
    }
}