import CollectionPage from "../../components/CollectionPage";
import { catalog } from "./catalog";

export default function AdminCertifications() {
  return <CollectionPage definition={catalog.certifications} />;
}
