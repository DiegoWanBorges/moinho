import { Route, Switch } from 'react-router';
import StockMovementForm from './Form';
import StockMovementList from './List';
import './styles.scss';

const Stockmovements = () => {
    return (
        <div>
            <Switch>
                <Route path="/stock/movements" exact>
                    <StockMovementList />
                </Route>
                <Route path="/stock/movements/:stockMovementId">
                    <StockMovementForm/>
                </Route>
            </Switch>
        </div>
    )
}

export default Stockmovements