import PrivateRoute from 'core/routes/PrivateRoutes'
import './styles.scss'

import { Switch } from 'react-router-dom'
import ApportionmentTypes from './Components/ApportionmentTypes'
import NavBarRegistration from './Components/NavBar'
import Parameters from './Components/Parameters'
import Users from './Components/Users'
import Groups from './Components/Groups'
import Units from './Components/Units'
import Product from './Components/Product'
import Sectors from './Components/Sectors'
import Employees from './Components/Employees'
import LaborCostTypes from './Components/LaborCosTypes'

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
                    <Units />
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
                <PrivateRoute path="/registrations/laborcosttypes">
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
