import { Route, Switch } from 'react-router';
import './styles.scss'


const OperationalPayments = () => {
    return (
        <div>
            <Switch>
                <Route path="/payments/operational" exact>
                    <h1>operational list</h1>
                </Route>
                <Route path="/payments/operational/:operationalPaymentId">
                    <h1>operational form</h1>
                </Route>
            </Switch>
        </div>
    )
}
export default OperationalPayments;