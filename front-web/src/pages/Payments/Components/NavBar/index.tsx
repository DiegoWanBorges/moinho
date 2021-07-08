import { isAllowedByRole } from 'core/utils/auth';
import { NavLink } from 'react-router-dom';
import './styles.scss'


const NavBarPayments = () => (
    <nav className="nav-payment-container">
        <ul className="nav-payment-list">
            {isAllowedByRole(["ROLE_ADMIN", "ROLE_LABOR_PAYMENT"]) ? (
                <li>
                    <NavLink to="/payments/labor" className="nav-payment-item">Mão de Obra</NavLink>
                </li>) : null}
            {isAllowedByRole(["ROLE_ADMIN", "ROLE_OPERATIONAL_PAYMENT"]) ? (
            <li>
                <NavLink to="/payments/operational" className="nav-payment-item">Operacionais</NavLink>
            </li>) : null}
        </ul>
    </nav>
)

export default NavBarPayments;