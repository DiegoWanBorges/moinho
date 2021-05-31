import PrivateRoute from 'core/routes/PrivateRoutes'

import { Switch } from 'react-router'
import LaborPayments from './Components/LaborPayment'
import NavBarPayments from './Components/NavBar'
import OperationalPayments from './Components/Operational'

import './styles.scss'
const Payment = () => {
    return (
        <div className="admin-container">
            <NavBarPayments />
            <div className="admin-content">
                <Switch>
                    <PrivateRoute path="/payments/labor" >
                        <LaborPayments/>
                    </PrivateRoute>
                    <PrivateRoute path="/payments/operational" >
                        <OperationalPayments/>
                    </PrivateRoute>
                </Switch>
            </div>
        </div>
    )

}
export default Payment
