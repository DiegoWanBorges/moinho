import { Route, Switch } from 'react-router';
import LaborPaymentForm from './Form';
import LaborPaymentList from './List';
import './styles.scss'


const LaborPayments = () => {
    return (
        <div>
            <Switch>
                <Route path="/payments/labor" exact>
                    <LaborPaymentList/>
                </Route>
                <Route path="/payments/labor/:laborPaymentId">
                    <LaborPaymentForm/>
                </Route>
            </Switch>
        </div>
    )
}
export default LaborPayments;