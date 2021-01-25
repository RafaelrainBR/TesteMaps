import React from 'react';
import ReactDOM from 'react-dom';
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";

import Dashboard from './views/dashboard';

import './index.css';
import Header from './component/header';
import Positions from './views/positions';

ReactDOM.render(
  <React.StrictMode>
    <Router>
      <Header>
        <Switch>
          <Route exact path="/">
            <Dashboard/>
          </Route> 
          <Route path="/position">
            <Positions/>
          </Route>
        </Switch>
      </Header>
    </Router>
  </React.StrictMode>,
  document.getElementById('root')
);