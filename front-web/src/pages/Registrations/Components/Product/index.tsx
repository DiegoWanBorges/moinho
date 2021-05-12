import { Route, Switch } from 'react-router';
import ProductForm from './Form';
import ProductList from './List';
import './styles.scss';

function Product() {
    return (
        <div>
            <Switch>
                <Route path="/registrations/products" exact>
                    <ProductList />
                </Route>
                <Route path="/registrations/products/:productId">
                    <ProductForm/>
                </Route>
            </Switch>
        </div>
    );
}
export default Product;
