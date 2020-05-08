import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UserSubscriptionService } from 'app/entities/user-subscription/user-subscription.service';
import { IUserSubscription, UserSubscription } from 'app/shared/model/user-subscription.model';

describe('Service Tests', () => {
  describe('UserSubscription Service', () => {
    let injector: TestBed;
    let service: UserSubscriptionService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserSubscription;
    let expectedResult: IUserSubscription | IUserSubscription[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserSubscriptionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UserSubscription(0, 0, currentDate, 'AAAAAAA', currentDate, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            validUpto: currentDate.format(DATE_FORMAT),
            nextRenewal: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserSubscription', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            validUpto: currentDate.format(DATE_FORMAT),
            nextRenewal: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            validUpto: currentDate,
            nextRenewal: currentDate,
            createdAt: currentDate,
            updatedAt: currentDate
          },
          returnedFromService
        );

        service.create(new UserSubscription()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserSubscription', () => {
        const returnedFromService = Object.assign(
          {
            price: 1,
            validUpto: currentDate.format(DATE_FORMAT),
            paymentGatewayToken: 'BBBBBB',
            nextRenewal: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            validUpto: currentDate,
            nextRenewal: currentDate,
            createdAt: currentDate,
            updatedAt: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserSubscription', () => {
        const returnedFromService = Object.assign(
          {
            price: 1,
            validUpto: currentDate.format(DATE_FORMAT),
            paymentGatewayToken: 'BBBBBB',
            nextRenewal: currentDate.format(DATE_FORMAT),
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            validUpto: currentDate,
            nextRenewal: currentDate,
            createdAt: currentDate,
            updatedAt: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserSubscription', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
