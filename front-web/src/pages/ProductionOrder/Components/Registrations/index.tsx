import { Route, Switch } from 'react-router';
import ProductionOrderCreate from './Create';
import ProductionOrderForm from './Form';
import ProductionOrderList from './List';

import './styles.scss';

function ProductionOrderRegistration() {
    return (
        <div>
            <Switch>
                <Route path="/productions/registrations" exact>
                    <ProductionOrderList/>
                </Route>
                <Route path="/productions/registrations/create" exact>
                    <ProductionOrderCreate/>
                </Route>
                <Route path="/productions/registrations/:productionOrderId">
                    <ProductionOrderForm />
                </Route>
            </Switch>
        </div>
    );
}
export default ProductionOrderRegistration;
