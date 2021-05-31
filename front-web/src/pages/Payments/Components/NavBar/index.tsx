import { NavLink } from 'react-router-dom';
import './styles.scss'


const NavBarPayments = () => (
    <nav className="nav-payment-container">
        <ul className="nav-payment-list">
            <li>
                <NavLink to="/payments/labor" className="nav-payment-item">Mão de Obra</NavLink>
            </li>

            <li>
                <NavLink to="/payments/operational" className="nav-payment-item">Operacionais</NavLink>
            </li>

            <li>
                <NavLink to="/payments/reports" className="nav-payment-item">Relatórios</NavLink>
            </li>
        </ul>
    </nav>
)

export default NavBarPayments;