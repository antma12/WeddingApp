import { Component, Input } from '@angular/core';

@Component({
  selector: 'positive-two-digit-number-input',
  templateUrl: './number-input.component.html',
  styles: [
    `
      em {
        color: #e05c65;
        padding-left: 15px;
        display: none;
      }
      .error-em {
        display: inline-block;
      }
      .error-in {
        border: solid #e05c65;
      }
      .error-in:focus {
        box-shadow: 0 0 0 0.2rem rgba(224, 92, 101, 0.25);
      }
    `,
  ],
})
export class NumberInput {
  @Input() label: string;
  @Input() nameId: string;
  @Input() nameName: string;
  isNotANumber: boolean;
  isLessThanZero: boolean;

  validateNumber = function (inputElement) {
    let value = inputElement.value;

    this.isLessThanZero = false;
    this.isNotANumber = false;

    inputElement.value = value.replaceAll(',', '.');
    value = inputElement.value;

    if (isNaN(value)) {
      this.isNotANumber = true;
      return;
    }

    if (+value < 0) {
      this.isLessThanZero = true;
      return;
    }
    if (value.indexOf('.') !== -1) {
      inputElement.value = value.slice(0, value.lastIndexOf('.') + 3);
    }
  };
}
