import { Routes } from '@angular/router';
import { TranslatorComponent } from './components/translator/translator.component';

export const routes: Routes = [
  { path: '', component: TranslatorComponent },
  { path: 'translator', component: TranslatorComponent },
];
