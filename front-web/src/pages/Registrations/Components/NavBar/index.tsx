import { NavLink } from 'react-router-dom';
import './styles.scss'


const NavBarRegistration = () => (
    <nav className="nav-registration-container">
        <ul className="nav-registration-list">
            
            <li>
                <NavLink to="/registrations/parameters" className="nav-registration-item">Parâmetros</NavLink>
            </li>
            
            <li>
                <NavLink to="/registrations/users" className="nav-registration-item">Usuários</NavLink>
            </li>

            <li>
                <NavLink to="/registrations/groups" className="nav-registration-item">Grupos</NavLink>
            </li>
            <li>
                <NavLink to="/registrations/units" className="nav-registration-item">Unidades</NavLink>
            </li>
            <li>
                <NavLink to="/registrations/products" className="nav-registration-item">Produtos</NavLink>
            </li>
            <li>
                <NavLink to="/registrations/sectors" className="nav-registration-item">Setores</NavLink>
            </li>
            <li>
                <NavLink to="/registrations/employees" className="nav-registration-item">Funcionários</NavLink>
            </li>
            <li>
                <NavLink to="/registrations/laborcostypes" className="nav-registration-item">Tipo de Pagamento</NavLink>
            </li>
            <li>
                <NavLink to="/registrations/apportionmenttypes" className="nav-registration-item">Tipo de Rateio</NavLink>
            </li>
            
        </ul>

    </nav>
)

export default NavBarRegistration;