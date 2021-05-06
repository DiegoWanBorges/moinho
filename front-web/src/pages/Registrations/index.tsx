import PrivateRoute from 'core/routes/PrivateRoutes'
import React from 'react'
import { Switch } from 'react-router-dom'
import ApportionmentTypes from './ApportionmentTypes'
import NavBarRegistration from './Components/NavBar'
import Employees from './Employees'
import Groups from './Groups'
import LaborCostTypes from './LaborCosTypes'
import Parameters from './Parameters'
import Product from './Product'
import Sectors from './Sectors'

import './styles.scss'
import Unity from './Unity'
import Users from './Users'

const Registration = () => (
    <div className="admin-container">
        <NavBarRegistration />
        <div className="admin-content">
            <Switch>
            <PrivateRoute path="/registrations/parameters" >
                    <Parameters />
                </PrivateRoute>
                <PrivateRoute path="/registrations/users" >
                    <Users />
                </PrivateRoute>
                <PrivateRoute path="/registrations/groups">
                    <Groups />
                </PrivateRoute>
                <PrivateRoute path="/registrations/units">
                    <Unity />
                </PrivateRoute>
                <PrivateRoute path="/registrations/products">
                    <Product />
                </PrivateRoute>
                <PrivateRoute path="/registrations/sectors">
                    <Sectors />
                </PrivateRoute>
                <PrivateRoute path="/registrations/employees">
                    <Employees />
                </PrivateRoute>
                <PrivateRoute path="/registrations/laborcostypes">
                    <LaborCostTypes />
                </PrivateRoute>
                <PrivateRoute path="/registrations/apportionmenttypes">
                    <ApportionmentTypes />
                </PrivateRoute>
            </Switch>
        </div>
    </div>
);

export default Registration;
