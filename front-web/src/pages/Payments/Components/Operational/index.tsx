import { Route, Switch } from 'react-router';
import OperationalPaymentForm from './Form';
import OperationalPaymentList from './List';
import './styles.scss'


const OperationalPayments = () => {
    return (
        <div>
            <Switch>
                <Route path="/payments/operational" exact>
                    <OperationalPaymentList/>
                </Route>
                <Route path="/payments/operational/:operationalPaymentId">
                    <OperationalPaymentForm/>
                </Route>
            </Switch>
        </div>
    )
}
export default OperationalPayments;