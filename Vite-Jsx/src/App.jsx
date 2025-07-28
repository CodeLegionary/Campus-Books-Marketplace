// src/App.jsx
import { Navigate, Route, BrowserRouter as Router, Routes } from "react-router-dom";
import "./App.css";
import NavBar from "./components/NavBar";
import Account from "./pages/Account";
import Card from "./pages/Card";
import LandingPage from "./pages/LandingPage"; // <-- Import the new LandingPage component

// Redirects to the backend login page
const RedirectToLogin = () => <Navigate to="http://localhost:8080/req/login" replace />;
// API_BASE_URL can stay here or be moved to LandingPage if it's only used there.
// For now, let's keep it here, as other pages might need it.
const API_BASE_URL = import.meta.env.VITE_APP_API_BASE_URL || "http://localhost:8080";

const App = () => (
  <Router>
    <NavBar />
    <Routes>
      <Route path="/" element={<MainContent />} /> {/* No adminEmail prop needed for MainContent */}
      <Route path="/card" element={<Card />} />
      <Route path="/account" element={<Account />} />
      <Route path="/req/login" element={<RedirectToLogin />} />
      <Route path="*" element={<MainContent />} />
    </Routes>
  </Router>
);

// MainContent component simplified further
const MainContent = () => {
  // No useLocation or conditional rendering logic needed here anymore.
  // MainContent simply serves as a wrapper or a place to render LandingPage.
  return (
    <div className="main-content">
      <LandingPage /> {/* No adminEmail prop passed to LandingPage for now */}
    </div>
  );
};

export default App;